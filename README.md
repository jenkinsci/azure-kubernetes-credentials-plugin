# gitlab-kubernetes-credentials-plugin

[![Build Status](https://ci.jenkins.io/job/Plugins/job/azure-kubernetes-credentials-plugin/job/main/badge/icon)](https://ci.jenkins.io/job/Plugins/job/azure-kubernetes-credentials-plugin/job/main/)
[![Jenkins Plugin](https://img.shields.io/jenkins/plugin/v/azure-kubernetes-credentials.svg)](https://plugins.jenkins.io/azure-kubernetes-credentials)
[![GitHub release](https://img.shields.io/github/release/jenkinsci/azure-kubernetes-credentials-plugin.svg?label=changelog)](https://github.com/jenkinsci/azure-kubernetes-credentials-plugin/releases/latest)
[![GitHub license](https://img.shields.io/github/license/jenkinsci/azure-kubernetes-credentials-plugin)](https://github.com/jenkinsci/azure-kubernetes-credentials-plugin/blob/master/LICENSE.md)
[![Jenkins Plugin Installs](https://img.shields.io/jenkins/plugin/i/azure-kubernetes-credentials.svg?color=blue)](https://plugins.jenkins.io/azure-kubernetes-credentials)


# Introduction

This plugin provides an extension for the [kubernetes-credentials-provider-plugin](https://github.com/jenkinsci/kubernetes-credentials-provider-plugin)
plugin, and the [azure-credentials-plugin](https://github.com/jenkinsci/azure-credentials-plugin) that extend the Kubernetes credentials provider to create the special credential type required by the azure-credentials when interacting with Azure Cloud.

## Usage

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
  subscripitonId: "MTIzNDQ1NjQ1Nwo=" # 1234456457
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
  subscripitonId: "MTIzNDQ1NjQ1Nwo=" # 1234456457
  clientId: "Y2xpZW50LWlkCg==" # client-id
  clientSecret: "Y2xpZW50LXNlY3JldC1zZWNyZXQtc3RyaW5nCg==" # client-secret-secret-string
  tenantId: "dGVuYW50LWlkCg==" # tenant-id
```

## LICENSE

Licensed under MIT, see [LICENSE](LICENSE)
