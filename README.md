# File Organizer

> A Java command-line tool that automatically sorts files into categorized subfolders based on file type.
---

## Overview

This program scans a source directory and moves files into subfolders (`Images`, `Documents`, `Audio`, `Video`, `Archives`, `Others`) based on their file extension.

## Features

- Sorts files into category folders automatically
- Deterministic duplicate handling (`file_1.txt`, `file_2.txt`, etc.) — never overwrites existing files
- Configurable source and target directories via command-line arguments
- Dry-run mode to preview planned moves without touching any files
- Handles files with no extension (sorted into `Others`)

## Tech Stack

| Tool | Purpose |
|------|---------|
| Java NIO (`java.nio.file`) | File system operations |
| HashMap | Extension-to-category mapping |

## Approach

### Usage

```bash
java FileOrganizer <source_directory> <target_directory> [--dry-run]
```

### Examples

Normal run (actually moves files):
```bash
java FileOrganizer "/Users/you/Desktop/TestFiles" "/Users/you/Desktop/TestOutput"
```

Preview only, no files moved:
```bash
java FileOrganizer "/Users/you/Desktop/TestFiles" "/Users/you/Desktop/TestOutput" --dry-run
```

## Sample Output
alag.mp3 -> alag.mp3
products.csv -> products.csv
Untitled.pdf -> Untitled.pdf
Screenshot 2026-09-17 at 7.18.21 PM.png -> Screenshot 2026-09-17 at 7.18.21 PM.png

See `DryRunLog.txt` for a full sample run log (dry-run mode).

##  Supported File Types

| Category | Extensions |
|----------|-----------|
| Images | jpg, jpeg, png, gif |
| Documents | pdf, doc, docx, txt |
| Audio | mp3, wav |
| Video | mp4, mkv, avi |
| Archives | zip, rar |
| Others | anything unrecognized or with no extension |

## License

This project is for educational purposes as part of an internship task.
