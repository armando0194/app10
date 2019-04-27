import os
import sys
import unittest
import datetime
import uuid

from flask_migrate import Migrate, MigrateCommand
from flask_script import Manager, Command

from app.main import create_app, db, mongo
from app.main.model.user import User
from app import blueprint

class FlagManager(Manager):
    def command(self, capture_all=False):
        def decorator(func):
            command = Command(func)
            command.capture_all_args = capture_all
            self.add_command(func.__name__, command)

            return func
        return decorator

app = create_app(os.getenv('BOILERPLATE_ENV') or 'dev')
app.register_blueprint(blueprint)
app.app_context().push()
manager = FlagManager(app)
migrate = Migrate(app, db)
manager.add_command('db', MigrateCommand)

@manager.command()
def run():
    app.run()

@manager.command()
def test():
    """Runs the unit tests."""
    tests = unittest.TestLoader().discover('app/test', pattern='test*.py')
    result = unittest.TextTestRunner(verbosity=2).run(tests)
    if result.wasSuccessful():
        return 0
    return 1

@manager.command(True)
def create_admin(*args):

    if len(args[0]) < 2:
        print("Usage: python manage.py create_admin [user] [password] ")

    admin = User(
            public_id=str(uuid.uuid4()),
            email="admin",
            username=sys.argv[2],
            password=sys.argv[3],
            admin= True,
            registered_on=datetime.datetime.utcnow()
        )

    db.session.add(admin)   
    db.session.commit()

if __name__ == '__main__':
    # Command.capture_all_args = True
    manager.run()

