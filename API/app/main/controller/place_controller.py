from flask import request
from flask_restplus import Resource
from ..util.dto import PlaceDto
from ..service.place_service import get_all_places
from ..util.decorator import token_required, admin_token_required
api = PlaceDto.api
_place = PlaceDto.place

@api.route('/')
class PlaceList(Resource):
    @api.doc('list of registered places')
    @api.marshal_list_with(_place, envelope='data')
    @token_required
    def get(self):
        """List all registered places"""
        return get_all_places()

    # @api.response(201, 'User successfully created.')
    # @api.doc('create a new user')
    # @api.expect(_user, validate=True)
    # @token_required
    # def post(self):
    #     """Creates a new User """
    #     data = request.json
    #     return save_new_user(data=data)


# @api.route('/<public_id>')
# @api.param('public_id', 'The User identifier')
# @api.response(404, 'User not found.')
# class User(Resource):
#     @api.doc('get a user')
#     @api.marshal_with(_user)
#     @admin_token_required
#     def get(self, public_id):
#         """get a user given its identifier"""
#         user = get_a_user(public_id)
#         if not user:
#             api.abort(404)
#         else:
#             return user