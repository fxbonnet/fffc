FROM ruby:2.5.3-stretch

RUN mkdir /opt/octo-fffc
COPY ./src/.build/octo-fffc-latest.gem /opt/octo-fffc

RUN  gem install \
        --local /opt/octo-fffc/octo-fffc-latest.gem \
        --no-ri --no-rdoc --force

WORKDIR /app

CMD [ "octo-fffc" ]