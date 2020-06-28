import os

from modules import factory


app = factory.create_app(app_name='Skillbot', config_name=os.environ["CONFIG_NAME"])


if __name__ == "__main__":
    app.run(debug=True, port=5000)