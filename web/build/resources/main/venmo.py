from com.peercoin.integration.currency import JythonPaymentMethod

class Venmo(JythonPaymentMethod):

    def __init__(self):
        pass

    def getName(self):
        return "Venmo"
