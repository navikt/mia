#Build container
FROM docker.adeo.no:5000/innsyn/innsyn-builder:0.0.1
ADD / /workspace

RUN /workspace/build.sh