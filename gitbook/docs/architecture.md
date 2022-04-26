---
sidebar_position: 2
title: Architecture
---

## High Level Architecture
![Architecture High-Level](/img/TDG_Architecture_Draft_v0.1.png)

## Component Architecture
The logical and technical architecture consists of three components:
- Database: Mongo DB. Off-the-shelf document-oriented database product.
- Backend: Server-component housing the test data generation, scenario handling and API to manage scenarios, templates and scenario instances. Git see [here](https://github.com/catenax-ng/product-test-data-generator/tree/main/tdg).
- Frontend: UI to manage and maintain scenarios, templates and scenario instances. Git see [here](https://github.com/catenax-ng/product-test-data-generator/tree/main/tdg-admin-ui).

## Technology Stack
- Progamming Language: Java > 12
- Database: Mongo DB
- Main Framework: Spring Boot
- API Framework: OpenAPI / Swagger
- UI Framework: Vaadin

