#!/usr/bin/env bash
set -e

REPO_NAME="looseScrew"
GITHUB_USER="eyeoverthink"

echo "🔩 Initializing $REPO_NAME for GitHub user $GITHUB_USER..."

git init
git checkout -B main

git add index.html LooseScrewEngine.java README.md deploy_loosescrew.sh
git commit -m "Manifest looseScrew: Music, Video & Code/REA Studio with EyeOverthink Avatar Host"

if command -v gh &> /dev/null; then
  gh repo create "$GITHUB_USER/$REPO_NAME" --public --source=. --remote=origin --push
else
  git remote add origin "https://github.com/$GITHUB_USER/$REPO_NAME.git" 2>/dev/null || true
  git push -u origin main
fi
echo "✅ Pushed live to https://github.com/$GITHUB_USER/$REPO_NAME"
