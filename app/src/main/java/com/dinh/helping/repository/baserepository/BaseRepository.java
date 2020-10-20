package com.dinh.helping.repository.baserepository;

import com.dinh.helping.repository.category.CategoryRepository;

public abstract class BaseRepository<V> {
    protected V instance;

    protected abstract V getInstance();
}
