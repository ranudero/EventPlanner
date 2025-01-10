---
name: New Feature request
about: Create a feature description as an issue for tracking new feature suggestions
title: "Feature: [FEATURE NAME]"
labels: ["enhancement"]
assignees: ''

---

# Issue Name

## Context

Explain what the feature should do and why it should do it.

## Design

```mermaid
---
title: Design via Class Diagram
---
classDiagram
    class Example {
        - Long id
        - String name
    }
```

```mermaid
---
title: Design via Entity Relation Diagram
---
erDiagram
    Example {
        int id PK
        varchar name
    }
```

## Process

Describe or draw the process that needs to be followed for the specific usecase of the issue.

## Acceptance Criteria

- [ ] Add needed acceptance criteria like endpoint naming, restrictions, happy and unhappy path outcomes, ...

## Getting Started
- [ ] Create a new Branch from main via Github.com
- [ ] Use the prompt to locally navigate to the new branch and change the upstream
- [ ] Start developing WITH tests
- [ ] Send pull request
- [ ] Wait for review
- [ ] Merge with main when review and tests have passed
