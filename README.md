# azure-kubernetes-credentials-plugin

## Introduction

This plugin provides an extension for the [kubernetes-credentials-provider-plugin](https://github.com/jenkinsci/kubernetes-credentials-provider-plugin)
plugin, and the [azure-credentials-plugin](https://github.com/jenkinsci/azure-credentials-plugin) that extend the Kubernetes credentials provider to create the special credential type required by the azure-credentials when interacting with Azure Cloud.


## Getting started

This plugin extends the kubernetes-credentials-provider-plugin to consume kubernetes secrets with a `"jenkins.io/credentials-type"` of `"azureManagedIdentity"` or `"azureServicePrincipal"`.
See examples below on how to use them, or see more examples in the test cases.

### Example of Azure Managed Identity

```
---
apiVersion: v1
kind: Secret
metadata:
  name: azure-managed-identity
  namespace: "testing"
  labels:
    "jenkins.io/credentials-type": "azureManagedIdentity"
    "jenkins.io/credentials-scope": "system"
  annotations:
    "jenkins.io/credentials-description": "Azure managed identity"
type: Opaque
data:
  subscriptionId: "MTIzNDQ1NjQ1Nwo=" # 1234456457
  clientId: "Y2xpZW50LWlkCg==" # client-id
```

### Example of Azure Service Principal
```
---
apiVersion: v1
kind: Secret
metadata:
  name: azure-service-principal
  namespace: "testing"
  labels:
    "jenkins.io/credentials-type": "azureServicePrincipal"
    "jenkins.io/credentials-scope": "system"
  annotations:
    "jenkins.io/credentials-description": "Azure service principal"
type: Opaque
data:
  subscriptionId: "MTIzNDQ1NjQ1Nwo=" # 1234456457
  clientId: "Y2xpZW50LWlkCg==" # client-id
  clientSecret: "Y2xpZW50LXNlY3JldC1zZWNyZXQtc3RyaW5nCg==" # client-secret-secret-string
  tenantId: "dGVuYW50LWlkCg==" # tenant-id
```

## Issues

Report issues and enhancements in the [Jenkins issue tracker](https://issues.jenkins.io/).

## Contributing

Refer to our [contribution guidelines](https://github.com/jenkinsci/.github/blob/master/CONTRIBUTING.md)

## LICENSE

Licensed under MIT, see [LICENSE](LICENSE.md)

