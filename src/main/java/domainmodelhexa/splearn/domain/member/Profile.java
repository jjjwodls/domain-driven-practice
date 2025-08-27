package domainmodelhexa.splearn.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public record Profile(@Column(length = 20) String address) {
    private static final Pattern PROFILE_ADDRESS_REGEX = Pattern.compile("[a-z0-9]+");

    public Profile{
        if(address == null || (!address.isEmpty()) && !PROFILE_ADDRESS_REGEX.matcher(address).matches()){
            throw new IllegalArgumentException("프로필 주소 형식이 올바르지 않습니다. : " + address);
        }

        if(address.length() > 15){
            throw new IllegalArgumentException("프로필 주소는 최대 15자리를 넘을 수 엇습니다.");
        }
    }

    public String url(){
        return "@" + address;
    }
}
