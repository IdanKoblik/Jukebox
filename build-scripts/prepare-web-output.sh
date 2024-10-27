#!/bin/bash

if [ ! -d $docs ]; then
  mkdir $docs
  unzip artifacts/webHelpW2-all.zip -d $docs

  mkdir -p $docs/shared
  mkdir -p $docs/paper
  mkdir -p $docs/adventure

  ls $docs/
  cp -a shared/build/docs/javadoc/* $docs/shared
  cp -a paper/build/docs/javadoc/* $docs/paper
  cp -a adventure/build/docs/javadoc/* $docs/adventure

  git add $docs/

  git commit -m "Output ready"

  git fetch origin
  git push origin pages
fi