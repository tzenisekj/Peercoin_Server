from com.peercoin.integration.currency import JythonPaymentMethod

class Cash(JythonPaymentMethod):

    def __init__(self):
        pass

    def getName(self):
        return "Cash By Mail"
