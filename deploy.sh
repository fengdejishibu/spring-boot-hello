#!/bin/bash
# deploy.sh - 在服务器上手动部署（可选）

REGISTRY="crpi-tm42n24q2axxf8ej.cn-shenzhen.personal.cr.aliyuncs.com"
IMAGE_NAME="spring-boot-hello"
VERSION="latest"
CONTAINER_NAME="spring-boot-hello"

echo "正在从 ACR 拉取镜像..."
docker pull ${REGISTRY}/${IMAGE_NAME}:${VERSION}

echo "停止并删除旧容器..."
docker stop ${CONTAINER_NAME} 2>/dev/null || true
docker rm ${CONTAINER_NAME} 2>/dev/null || true

echo "启动新容器..."
docker run -d \
  --name ${CONTAINER_NAME} \
  --restart unless-stopped \
  -p 8080:8080 \
  ${REGISTRY}/${IMAGE_NAME}:${VERSION}

echo "部署完成!"
docker ps | grep ${CONTAINER_NAME}
