#!/usr/bin/env bash
set -Eeuo pipefail

ROOT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
JAVA_DIR="$ROOT_DIR/java"

if [[ ! -d "$JAVA_DIR" ]]; then
  echo "[error] Expected directory not found: $JAVA_DIR" >&2
  exit 1
fi

cd "$JAVA_DIR"

# `ie.tudublin.Main` calls: PApplet.runSketch(..., new IAMDANI())
# So running Main is the canonical way to launch IAMDANI in this repo.

# Compatibility shim:
# The currently-built classes expect assets under "./java/data" while running from the
# java/ working directory. When we `cd java/`, that path becomes "java/java/data".
# Create a symlink so the sketch can find fonts/models without code changes.
if [[ ! -e "./java/data" ]]; then
  mkdir -p ./java
  ln -s ../data ./java/data
fi

# Note: java/mrun.sh isn’t always executable, so invoke it via bash.
exec bash "./mrun.sh"
