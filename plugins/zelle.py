from com.peercoin.integration.currency import JythonPaymentMethod

class Zelle(JythonPaymentMethod):

    def __init__(self):
        pass

    def getName(self):
        return "Zelle"
