#!/usr/bin/env bash

sbt "testOnly TestCases.PositiveSpec" "testOnly TestCases.NegativeSpec"
