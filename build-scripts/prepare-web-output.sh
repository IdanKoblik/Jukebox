#!/bin/bash

if [ ! -d "docs" ]; then
  mkdir $docs
  unzip artifacts/webHelpCB2-all.zip -d $docs
  mkdir -p $docs/adventure
  mkdir -p $docs/paper
  mkdir -p $docs/shared

  ls $docs/
  cp -a common/build/docs/javadoc/* $docs/adventure
  cp -a spigot/build/docs/javadoc/* $docs/paper
  cp -a commands/build/docs/javadoc/* $docs/shared
fi