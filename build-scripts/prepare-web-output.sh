#!/bin/bash

if [ ! -d $docs ]; then
  mkdir $docs
  unzip artifacts/webHelpHI2-all.zip -d $docs

  git rm -rf artifacts

  mkdir -p $docs/shared
  mkdir -p $docs/paper
  mkdir -p $docs/adventure

  ls $docs/
  cp -a shared/build/docs/javadoc/* $docs/shared
  cp -a paper/build/docs/javadoc/* $docs/paper
  cp -a adventure/build/docs/javadoc/* $docs/adventure

  git rm -rf shared
  git rm -rf paper
  git rm -rf adventure
  git rm -rf Writerside

  git add $docs/

  git commit -m "Output ready"
fi