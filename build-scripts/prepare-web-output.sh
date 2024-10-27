#!/bin/bash

git reset --hard
git fetch origin

if [ ! -d "docs" ]; then
  mkdir $docs
  unzip artifacts/webHelpHI2-all.zip -d $docs
  mkdir -p $docs/shared
  mkdir -p $docs/paper
  mkdir -p $docs/adventure

  ls $docs/
  cp -a shared/build/docs/javadoc/* $docs/shared
  cp -a paper/build/docs/javadoc/* $docs/paper
  cp -a adventure/build/docs/javadoc/* $docs/adventure

  git update-index --assume-unchanged .gradle/
  git update-index --assume-unchanged .idea/
  git update-index --assume-unchanged adventure/
  git update-index --assume-unchanged artifacts/
  git update-index --assume-unchanged build/
  git update-index --assume-unchanged paper/
  git update-index --assume-unchanged shared/

  git commit -m "Create docs dir"
fi