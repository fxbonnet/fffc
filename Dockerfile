FROM php:7.1-fpm

RUN export LC_ALL=C

RUN pecl install xdebug-2.5.5 \
    && docker-php-ext-enable xdebug

RUN apt-get update && apt-get install -y --no-install-recommends \
            curl \
            build-essential \
            apt-utils \
            libmcrypt-dev \
            openssh-client \
            libpng12-dev \
            libjpeg62-turbo-dev \
            apt-transport-https \
            libfreetype6-dev \
    && docker-php-ext-install -j$(nproc) iconv mcrypt \
    && docker-php-ext-configure gd --with-freetype-dir=/usr/include/ --with-jpeg-dir=/usr/include/ \
    && docker-php-ext-install -j$(nproc) gd \
    && docker-php-ext-install -j$(nproc) \
            pdo_mysql \
            zip

# CLEAN UP
RUN apt-get clean \
    && rm -r /var/lib/apt/lists/* \
    && rm -rf /tmp/pear

#LARAVEL PHP-FPM ----------------------------------------------------------------------------------------------
COPY ./docker-config/php-fpm/conf/laravel.conf /usr/local/etc/php-fpm.d/laravel.conf

#LARAVEL ------------------------------------------------------------------------------------------------------
ENV INITRD No
ENV LANG en_US.UTF-8

# Get composer
ENV COMPOSER_ALLOW_SUPERUSER 1
RUN /usr/bin/curl -sS https://getcomposer.org/installer |/usr/local/bin/php \
    && /bin/mv composer.phar /usr/local/bin/composer

# get our destination folder
RUN mkdir -p /var/www/html/

# Better way to do composer dependencies  in terms of caching
COPY ./src/composer.json /var/www/html/composer.json
COPY ./src/composer.lock /var/www/html/composer.lock
RUN cd /var/www/html/
RUN composer install --no-scripts --no-autoloader

# SOURCE CODE  ----------------------------------------------------------------------------------------------
COPY ./src/ /var/www/html/

# SET FILE PERMISSION --------------------------------------------------------------------------------------
RUN chown -R :www-data /var/www/html \
 && chmod -R ug+rwx /var/www/html/storage /var/www/html/bootstrap/cache

# complete composer instsall
RUN cd /var/www/html/
#RUN composer dump-autoload --optimize && \
#	composer run-script post-root-package-install post-create-project-cmd post-autoload-dump