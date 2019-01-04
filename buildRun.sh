#!/bin/bash

mvn install -DskipTests

nuhup mvn --projects backend spring-boot:run