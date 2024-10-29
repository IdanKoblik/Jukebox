#!/bin/bash

if [ "$#" -ne 3 ]; then
  echo "Usage: $0 <version> <prev_version> <is_minor>"
  exit 1
fi

version="$1"
prev_version="$2"
is_minor="$3"

update_json() {
  json_file="help-versions.json"
  local new_element=$(cat <<EOF
{
  "version": "$version",
  "url": "/$version/",
  "isCurrent": true
}
EOF
)

  if [ "$is_minor" == "true" ]; then
    if [ -d "$prev_version" ]; then
      echo "Creating old version directory for version $prev_version"
      git rm -rf "$prev_version/" || true
      git commit -m "Removed old version" || echo "No changes to commit"

      updated_json=$(jq --arg version "$version" '
        .[-1].version = $version |
        .[-1].url = "/\($version)\/" |
        .[-1].isCurrent = true |
        .[0:-1] |= map(.isCurrent = false)
      ' "$json_file")

      echo "$updated_json" > "$json_file"
      git add help-versions.json
      git commit -m "Override docs in github pages branch" || echo "No changes to commit"
    else
      echo "Previous version directory not found: $prev_version"
      exit 1
    fi
  else
    updated_json=$(jq --argjson new "$new_element" '
      map(.isCurrent = false) + [$new]
    ' "$json_file")

    echo "$updated_json" > "$json_file"
    git add help-versions.json
    git commit -m "Update docs in github pages branch" || echo "No changes to commit"
  fi
}

git fetch origin || true
git checkout gh-pages origin/gh-pages || git checkout gh-pages
git pull origin gh-pages || true

update_json

git push origin gh-pages
