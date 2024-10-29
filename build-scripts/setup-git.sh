#!/bin/bash

git remote rm origin
git remote add origin git@github.com:${{ github.repository }}
git config --local user.email "actions@github.com"
git config --local user.name "GitHub Actions"