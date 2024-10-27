#!/bin/bash

git reset --hard
git fetch origin

if [ ! -d $docs ]; then
  mkdir $docs
  unzip artifacts/webHelpHI2-all.zip -d $docs
  mkdir -p $docs/shared
  mkdir -p $docs/paper
  mkdir -p $docs/adventure

  ls $docs/
  cp -a shared/build/docs/javadoc/* $docs/shared
  cp -a paper/build/docs/javadoc/* $docs/paper
  cp -a adventure/build/docs/javadoc/* $docs/adventure

  git add $docs/

  git rm .gradle/
  git rm .idea/
  git rm adventure/
  git rm build/
  git rm paper/
  git rm shared/

  git commit -m "Create docs dir"
fi