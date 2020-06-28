import os

basedir = os.path.abspath(os.path.dirname(__file__))


class Config:
    def __init__():
        pass

class DevelopmentConfig(Config):
    DEBUG = True

class ProductionConfig(Config):
    DEBUG = False

config = {
    'development': DevelopmentConfig,
    'production': ProductionConfig,
    'local': DevelopmentConfig
}