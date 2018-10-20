FROM python:3.6-alpine3.7 AS runner
RUN mkdir -p /app
COPY requirements.txt /app/
pip install -r requirements.txt

COPY solutions/converter /app/
COPY solutions/main.py /app/

WORKDIR /app

