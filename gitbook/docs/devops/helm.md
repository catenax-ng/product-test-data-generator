---
sidebar_position: 2
title: Helm Charts for TDG
---

Please find the current helm charts for the TDF and its components via [Git](https://github.com/catenax-ng/product-test-data-generator/tree/main/chart/testdatagenerator).

The following helm charts for the product have been created:

- Chart: *Chart.yaml* 
    - Is the main chart for the product
    - References all dependencies to the product
- Values
    - *values.yaml*: default values for deployment
    - *values-int.yaml*: values for deployment on int-env of hotel budapest
- Deployments
    - *templates/deployment-be.yaml*: Deployment descriptor for backend component
    - *templates/deployment-fe.yaml*: Deployment descriptor for frontend component
- Services
    - *templates/service-be.yaml*: Service definition for backend component
    - *templates/service-fe.yaml*: Service definition for frontend component
- Ingress Controller
    - *templates/ingress.yaml*
- Dependencies
    - *MongoDB* chart from bitnami version 11.1.7

Documentation and entry into helm charts can be found [here](https://helm.sh)