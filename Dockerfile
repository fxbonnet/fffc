FROM python:3.6-alpine3.7 AS runner
RUN apk --update add --no-cache g++
RUN mkdir -p /app
WORKDIR /app

COPY requirements.txt /app/
RUN pip install -r requirements.txt

COPY solutions/converter /app/
COPY solutions/main.py /app/

