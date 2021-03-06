{{/*
Expand the name of the chart.
*/}}
{{- define "testdatagenerator.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "testdatagenerator.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{- define "testdatagenerator.selectorLabels" -}}

{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "testdatagenerator.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "testdatagenerator.be.labels" -}}
app.kubernetes.io/name: be-{{ include "testdatagenerator.name" . }}
helm.sh/chart: {{ include "testdatagenerator.chart" . }}
{{ include "testdatagenerator.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "testdatagenerator.fe.labels" -}}
app.kubernetes.io/name: fe-{{ include "testdatagenerator.name" . }}
helm.sh/chart: {{ include "testdatagenerator.chart" . }}
{{ include "testdatagenerator.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}


{{/*
Selector labels backend
*/}}
{{- define "testdatagenerator.be.selectorLabels" -}}
app.kubernetes.io/name: be-{{ include "testdatagenerator.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Selector labels frontend
*/}}
{{- define "testdatagenerator.fe.selectorLabels" -}}
app.kubernetes.io/name: fe-{{ include "testdatagenerator.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "testdatagenerator.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "testdatagenerator.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}
