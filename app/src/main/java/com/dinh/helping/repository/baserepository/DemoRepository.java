package com.dinh.helping.repository.baserepository;

public class DemoRepository extends BaseRepository<DemoRepository> {

    public static DemoRepository instance;
    @Override
    protected DemoRepository getInstance() {
        if (instance == null) {
            instance = new DemoRepository();
        }
        return instance;
    }
}
