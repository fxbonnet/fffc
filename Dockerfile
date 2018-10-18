FROM python:3.6-alpine3.7 AS runner
RUN mkdir -p /app

COPY solutions/converter /app/
COPY solutions/main.py /app/

WORKDIR /app

