# From Monolith to Modular Monolith 
# Modular Monolith Pitfalls (Grails Demo)

This repository is an educational Grails project designed to **intentionally demonstrate common architectural pitfalls** encountered when working with **monolithic applications** — especially when trying to move toward a **modular monolith**.

---

## 🎯 Purpose

The primary goal of this codebase is to:

- **Showcase the challenges** that arise when domains are tightly coupled inside a monolithic system.
- Serve as a **learning tool** for engineers and architects to identify anti-patterns.
- Provide a foundation that can later be **refactored toward a modular monolith** or even microservices.

---

## 🧱 Current Structure

The app models a simplified e-commerce domain with:

- `Customer`
- `Order`
- `ShippingInfo`
- `Address`

All domain logic and data access live inside a single Grails app — but cross-domain interactions, shared entities, and eager loading highlight key monolith issues.

---

## 🔥 Intentional Pitfalls in the Code

| Area | Pitfall | Example |
|------|---------|---------|
| Cross-domain entity dependencies | `Order` has a direct reference to `Customer` and `ShippingInfo` | `Order.customer`, `Order.shippingInfo` |
| Deep object graphs | `Customer` has a `hasMany` relationship to `Order` | `Customer.orders` |
| Static GORM access | Services directly call `Order.get()` or `Order.findAllByIdInList()` | `OrderService.getOrderDetails()` |
| Service-to-service tight coupling | `OrderService` depends on `CustomerService` from another domain | `customerService.getCustomersByIds(...)` |
| Shared mutable entities | `Address` is used across domains (`Customer`, `ShippingInfo`) | see `Address.groovy` |

---

## ✅ What Works

- All services and controllers are fully functional.
- Unit tests are provided (e.g., `OrderServiceSpec`) to validate functionality while demonstrating testing pain points caused by coupling.
- The code is ready to be **refactored module-by-module** to demonstrate best practices.

---

## 🚧 Next Steps (Possible Refactors)

This repo is a perfect launchpad for a "modular journey". Future improvements could include:

- Introduce **domain modules** (`customer`, `order`) with clear ownership.
- Replace direct GORM usage with **repositories** or **query ports**.
- Replace domain object passing with **DTOs** for cross-module interaction.
- Remove bidirectional relationships (`Customer.orders`).
- Extract `Address` into **value objects** scoped per module.
- Add interface-driven boundaries and **Ports and Adapters**.

---

## 🧪 Running the App

To run the project locally:

```bash
./gradlew bootRun
```

## 🐢 Simulated Build Slowdowns

To simulate how large monolithic applications often suffer from **slow compilation and packaging**, artificial delays have been added to the Gradle build process:

```groovy
tasks.withType(GroovyCompile).configureEach {
    doFirst {
        println "Big app: Simulating slow Groovy compile..."
        Thread.sleep(10000)
    }
}

tasks.named("bootJar") {
    doFirst {
        println "Big app: Simulating slow bootJar packaging..."
        Thread.sleep(5000)
    }
}
```