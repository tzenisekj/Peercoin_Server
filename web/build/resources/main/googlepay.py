from com.peercoin.integration.currency import JythonPaymentMethod

class GooglePay(JythonPaymentMethod):

    def __init__(self):
        pass

    def getName(self):
        return "Google Pay"
