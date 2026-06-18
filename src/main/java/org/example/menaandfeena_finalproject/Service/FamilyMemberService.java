package org.example.menaandfeena_finalproject.Service;

import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiException;
import org.example.menaandfeena_finalproject.Model.FamilyMember;
import org.example.menaandfeena_finalproject.Model.User;
import org.example.menaandfeena_finalproject.Repository.FamilyMemberRepository;
import org.example.menaandfeena_finalproject.Repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyMemberService {
    private final FamilyMemberRepository familyMemberRepository;
    private final UserRepository userRepository;


    public List<FamilyMember> getAll() {
        return familyMemberRepository.findAll();
    }


    public void add(Integer userId, FamilyMember familyMember) {
        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new ApiException("Associated user not found");
        }

        familyMember.setUser(user);
        familyMemberRepository.save(familyMember);
    }



    public void update(Integer id, FamilyMember familyMember) {
        FamilyMember old = familyMemberRepository.findFamilyMemberById(id);
        if (old == null) throw new ApiException("Family member not found");
        old.setName(familyMember.getName());
        old.setAge(familyMember.getAge());
        old.setGender(familyMember.getGender());
        old.setRelation(familyMember.getRelation());
        familyMemberRepository.save(old);
    }

    public void delete(Integer id) {
        FamilyMember familyMember = familyMemberRepository.findFamilyMemberById(id);
        if (familyMember == null) throw new ApiException("Family member not found");
        familyMemberRepository.delete(familyMember);
    }

    //Reenad
    public List<FamilyMember> getMyFamily(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new ApiException("User not found");
        }
        return familyMemberRepository.findByUserId(userId);
    }

}
