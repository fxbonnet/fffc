FROM ruby:2.5.3-stretch

# guard support for continuous unit test execution
RUN gem install guard --no-ri --no-rdoc \
    && gem install guard-yield --no-ri --no-rdoc 

# app depds
RUN mkdir /opt/fffc-depds
COPY ./src/octo-fffc/Gemfile /opt/fffc-depds
RUN cd /opt/fffc-depds && bundle install

# app workspace
WORKDIR /app