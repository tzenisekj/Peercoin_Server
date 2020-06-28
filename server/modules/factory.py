from flask import Flask
from config import config

from modules.currency.currency_controller import currency_api


def create_app(app_name, config_name):
    app = Flask(app_name, static_url_path='')
    app.config.from_object(config[config_name])
    register_blueprints(app)
    return app


def register_blueprints(app):
    app.register_blueprint(currency_api)
