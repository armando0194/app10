from flask_restplus import Namespace, fields


class PlaceDto:
    api = Namespace('place', description='place related operations')
    place = api.model('place', {
        'name' : fields.String(required=True, description='name of the place'),
        'place_id' : fields.String(required=True, description='unique place identifier')
    })

class UserDto:
    api = Namespace('user', description='user related operations')
    user = api.model('user', {
        'email': fields.String(required=True, description='user email address'),
        'username': fields.String(required=True, description='user username'),
        'password': fields.String(required=True, description='user password'),
        'public_id': fields.String(description='user Identifier')
    })

class AuthDto:
    api = Namespace('auth', description='authentication related operations')
    user_auth = api.model('auth_details', {
        'email': fields.String(required=True, description='The email address'),
        'password': fields.String(required=True, description='The user password '),
    })
