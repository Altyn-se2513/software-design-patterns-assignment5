# Assignment #5 - Strategy Pattern: E-Commerce Checkout System

## Overview

This project is an e-commerce checkout system where the discount calculation logic
can be switched at runtime. I used the Strategy pattern to separate the checkout
process from the actual discount algorithms. The Context class does not know how
the discount is calculated; it only asks the Strategy to do it.

`NoDiscountStrategy` also acts as a Null Object: the Context always has a valid
strategy, so callers do not need a separate null check when no discount is
wanted.

## Project Structure

```text
src/ecommerce/
  Main.java
  context/
    CheckoutContext.java
  discount/
    DiscountStrategy.java
    NoDiscountStrategy.java
    PercentageDiscountStrategy.java
    FixedAmountDiscountStrategy.java
    TieredDiscountStrategy.java
  model/
    Cart.java
    Product.java 
```

## Clean Code Principles Applied and Justified
### 1. Polymorphism instead of conditionals
Where: CheckoutContext.calculateTotal() has no if or switch on the
strategy type. It calls strategy.applyDiscount(...).
Why it matters: Java chooses the correct implementation at runtime. Adding a
new discount does not add another branch to the Context.

> **Note on `if` inside strategies:** The `if` statements in
> `FixedAmountDiscountStrategy` (minimum-spend check) and
> `TieredDiscountStrategy` (threshold selection) are part of the discount
> algorithm itself, not strategy selection. The assignment forbids `if`/`switch`
> on strategy type inside the Context; these are algorithm rules, not branching
> between strategies.

### 2. Single Responsibility
Where: Each strategy class has one reason to change: its own discount formula.
Cart only calculates the subtotal, CheckoutContext only delegates the
calculation, and Product only stores product data.

Why it matters: A change in the percentage formula cannot accidentally break
the fixed-amount discount or the checkout flow.

### 3. Meaningful, intention-revealing names
Where: PercentageDiscountStrategy, FixedAmountDiscountStrategy,
TieredDiscountStrategy, applyDiscount, minimumSpend,
getActiveStrategyDescription.

Why it matters: The names explain the intent without extra comments. Strategy
descriptions match their rules: the fixed discount is $50.00 off orders of
$900.00 or more, and the tiered discount applies at orders of $500.00 or more
and $1,000.00 or more.

### 4. Algorithm-agnostic Context / Dependency Inversion
Where: CheckoutContext imports only DiscountStrategy, never a concrete
strategy class.

Why it matters: The Context depends on the abstraction, not on concrete
implementations. The client (Main) decides which strategy to use.

### 5. Open/Closed Principle — demonstrated
Where: TieredDiscountStrategy was added as a fourth strategy. To support the
new algorithm, no changes were needed in CheckoutContext or in the existing
strategy classes.

Why it matters: The system is open for extension but closed for modification.
This is demonstrated in code, not only described in the README.

### 6. Bonus: fail fast and immutability
Where: Constructors validate input:
PercentageDiscountStrategy checks 0..100;
FixedAmountDiscountStrategy rejects negative values;
Product rejects a null or negative price;
Cart rejects a null product;
CheckoutContext rejects null in both the constructor and the setter.
Strategy fields are final.

Why it matters: An invalid strategy cannot be created, so the error is found
at the place where it was made, not later during checkout.

## How to run code 
```
javac -d out $(find src -name "*.java")
java -cp out ecommerce.Main
```
Example output for a cart subtotal of $1,000.00:

```text
Cart Subtotal: $1000.00
No Discount -> Final: $1000.00
10% off -> Final: $900.00
$50.00 off orders of $900.00 or more -> Final: $950.00
5% off orders of $500.00 or more, 15% off orders of $1000.00 or more -> Final: $850.00
```
