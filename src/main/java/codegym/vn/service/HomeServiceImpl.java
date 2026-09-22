package codegym.vn.service;

import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService{

    @Override
    public String getName() {
        return "Nguyen Tien Dat";
    }
}
