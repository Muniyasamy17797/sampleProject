#!/bin/sh

exec "$@"
# This script is used as an entry point for a Docker container.
# It simply executes the command passed to it as arguments.
# The `exec` command replaces the shell with the command specified in the arguments.
# This is useful for running a command in the foreground, allowing the container to run as expected.
# The script does not perform any additional actions or checks.
# It is a simple and effective way to set up a container to run a specific command or application.
# The script is typically used in conjunction with a Dockerfile, where it is specified as the entry point for the container.
# The script is executed when the container starts, and the command passed to it is run in the container's environment.