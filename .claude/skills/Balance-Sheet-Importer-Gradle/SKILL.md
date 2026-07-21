```markdown
# Balance-Sheet-Importer-Gradle Development Patterns

> Auto-generated skill from repository analysis

## Overview
This skill teaches you the development patterns and conventions used in the `Balance-Sheet-Importer-Gradle` TypeScript codebase. You'll learn about file naming, import/export styles, commit practices, and how to write and run tests, as well as suggested commands for common workflows. This repository does not use a detected framework, focusing on plain TypeScript.

## Coding Conventions

### File Naming
- Use **camelCase** for file names.
  - Example: `balanceSheetImporter.ts`

### Import Style
- Use **relative imports** for referencing modules.
  - Example:
    ```typescript
    import { parseBalanceSheet } from './parser';
    ```

### Export Style
- Use **named exports** to expose functions or constants.
  - Example:
    ```typescript
    export function importBalanceSheet(data: string): BalanceSheet { ... }
    ```

### Commit Patterns
- Commit messages are **freeform** (no enforced prefixes).
- Average commit message length: ~23 characters.

## Workflows

### Running the Application
**Trigger:** When you want to execute the main balance sheet importer logic.
**Command:** `/run-app`

1. Ensure dependencies are installed (`npm install`).
2. Compile the TypeScript code (`tsc` or via your build tool).
3. Run the entry point script (e.g., `node dist/balanceSheetImporter.js`).

### Adding a New Feature
**Trigger:** When implementing new functionality.
**Command:** `/add-feature`

1. Create a new TypeScript file using camelCase naming.
2. Use relative imports to include dependencies.
3. Export new functions or constants using named exports.
4. Write corresponding tests in a `.test.ts` file.
5. Commit changes with a clear, concise message.

### Writing and Running Tests
**Trigger:** When verifying code correctness.
**Command:** `/run-tests`

1. Write test files matching the `*.test.*` pattern (e.g., `importer.test.ts`).
2. Use your preferred test runner (framework not detected; common options: Jest, Mocha).
3. Run tests via the test runner's CLI (e.g., `npx jest` or `npx mocha`).

## Testing Patterns

- Test files are named with the `*.test.*` pattern (e.g., `parser.test.ts`).
- The specific testing framework is **unknown**; adapt to your team's preferred tool.
- Place tests alongside or near the code they test.

**Example:**
```typescript
// parser.test.ts
import { parseBalanceSheet } from './parser';

describe('parseBalanceSheet', () => {
  it('should parse valid data', () => {
    const data = '...';
    const result = parseBalanceSheet(data);
    expect(result).toBeDefined();
  });
});
```

## Commands
| Command      | Purpose                                      |
|--------------|----------------------------------------------|
| /run-app     | Build and run the main application           |
| /add-feature | Scaffold and implement a new feature         |
| /run-tests   | Execute all test files in the codebase       |
```
