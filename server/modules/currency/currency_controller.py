from flask import Blueprint, request, Response, jsonify

currency_api = Blueprint('currency_api', __name__)

@currency_api.route('/api/v1/currency', methods=['GET','POST'])
def get_or_create_currency():
    if request.method == 'GET':
        return Response({},200)
    elif request.method == 'POST':
        try:
            return Response({},201)
        except Exception as e:
            logging.error('Unhandled exception while adding new currency {}'.format(e))
            return Response({},500)