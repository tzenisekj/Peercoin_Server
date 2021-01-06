package com.peercoin.web.services.helpers;

import java.util.List;

public interface ModelGenerator<T> {
    T generateOne();
    List<T> generateMany();
}
