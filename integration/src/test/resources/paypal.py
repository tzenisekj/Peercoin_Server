from com.peercoin.integration.currency import JythonPaymentMethod

class Paypal(JythonPaymentMethod):

    def __init__(self):
        pass

    def getName(self):
        return "Paypal"