from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_bcrypt import Bcrypt
from flask_pymongo import PyMongo
from werkzeug.debug import DebuggedApplication

from .config import config_by_name

db = SQLAlchemy()
flask_bcrypt = Bcrypt()
mongo = PyMongo()

def create_app(config_name):
    app = Flask(__name__)
    app.config.from_object(config_by_name[config_name])
    app.config["MONGO_URI"] = 'mongodb://databasejp:NzJiJDw2Od3kYuiJisrNdzZl6GMKj0tcf7olbfI6vAeqGaqssN7YrGTIaCAuUVlly16TXGDVwnjbiqhnUUulYA==@databasejp.documents.azure.com:10255/?ssl=true&replicaSet=globaldb'
    db.init_app(app)
    mongo.init_app(app)
    flask_bcrypt.init_app(app)

    return app