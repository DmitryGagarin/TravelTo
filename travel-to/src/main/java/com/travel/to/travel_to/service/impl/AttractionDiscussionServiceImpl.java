package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.constants.FormatConstants;
import com.travel.to.travel_to.entity.attraction.AttractionDiscussion;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.user.Roles;
import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.entity.user.UserToRole;
import com.travel.to.travel_to.form.AttractionDiscussionCreateForm;
import com.travel.to.travel_to.repository.AttractionDiscussionRepository;
import com.travel.to.travel_to.service.AttractionDiscussionImageService;
import com.travel.to.travel_to.service.AttractionDiscussionService;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.RoleService;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.service.UserToRoleService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AttractionDiscussionServiceImpl implements AttractionDiscussionService {

    private final UserService userService;
    private final RoleService roleService;
    private final AttractionService attractionService;
    private final AttractionDiscussionImageService attractionDiscussionImageService;
    private final AttractionDiscussionRepository attractionDiscussionRepository;
    private final UserToRoleService userToRoleService;

    @Autowired
    public AttractionDiscussionServiceImpl(
        UserService userService,
        RoleService roleService,
        AttractionService attractionService,
        AttractionDiscussionImageService attractionDiscussionImageService,
        AttractionDiscussionRepository attractionDiscussionRepository,
        UserToRoleService userToRoleService
    ) {
        this.userService = userService;
        this.roleService = roleService;
        this.attractionService = attractionService;
        this.attractionDiscussionImageService = attractionDiscussionImageService;
        this.attractionDiscussionRepository = attractionDiscussionRepository;
        this.userToRoleService = userToRoleService;
    }

    @Override
    @NotNull
    @Transactional
    public AttractionDiscussion create(
        @NotNull AttractionDiscussionCreateForm attractionDiscussionCreateForm,
        @NotNull AuthUser authUser,
        @NotNull String attractionUuid,
        @NotNull MultipartFile[] images
    ) throws IOException {
        Long attractionId = attractionService.getByUuid(attractionUuid).getId();

        AttractionDiscussion attractionDiscussion = new AttractionDiscussion();
        attractionDiscussion
            .setTitle(attractionDiscussionCreateForm.getTitle())
            .setContentLike(attractionDiscussionCreateForm.getContentLike())
            .setContentDislike(attractionDiscussionCreateForm.getContentDislike())
            .setContent(attractionDiscussionCreateForm.getContent())
            .setRating(attractionDiscussionCreateForm.getRating())
            .setAuthorId(userService.getByUuid(authUser.getUuid()))
            .setAttractionId(attractionId)
            .setCreatedAt(LocalDateTime.now())
            .setUpdatedAt(LocalDateTime.now());
        attractionDiscussionRepository.save(attractionDiscussion);

        userService.updateUserRole(authUser, Roles.DISCUSSION_OWNER);

        Set<GrantedAuthority> roles = null;
        Optional<User> userOptional = userService.findByEmail(authUser.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            roles = userToRoleService.getAllUserRolesByUserId(user.getId());
        }

        if (roles != null && !roles.contains(new SimpleGrantedAuthority("ROLE_" + Roles.DISCUSSION_OWNER))) {
            UserToRole userToRole = new UserToRole();
            userToRole
                .setUser(userService.getByUuid(authUser.getUuid()))
                .setRole(roleService.getRoleByName(Roles.DISCUSSION_OWNER.toString()));
            userToRoleService.save(userToRole);
        }

        attractionDiscussionImageService.create(attractionDiscussion.getId(), images);

        List<Double> ratings = attractionService.findAllAttractionRatingByAttractionId(attractionId);
        ratings.add(attractionDiscussion.getRating());
        double totalRating = ratings.stream().mapToDouble(i -> i).sum();
        double averageRating = totalRating / attractionDiscussionRepository.findAll().size();
        String formattedAverageRating = FormatConstants.ONE_FLOATING_POINT_FORMATTER.format(averageRating);

        attractionService.updateRating(attractionUuid, Double.parseDouble(formattedAverageRating.replace(',','.')));

        return attractionDiscussion;
    }

    @Override
    public void delete(AuthUser authUser, String attractionUuid) {
        AttractionDiscussion attractionDiscussion = getByUuid(attractionUuid);
        attractionDiscussionRepository.delete(attractionDiscussion);
    }

    @Override
    @NotNull
    public AttractionDiscussion getByUuid(String attractionUuid) {
        return attractionDiscussionRepository.getByUuid(attractionUuid);
    }

    @Override
    @NotNull
    public List<AttractionDiscussion> findAllByAttractionUuid(String attractionUuid) {
        return attractionDiscussionRepository.findAllByAttractionId(
            attractionService.getByUuid(attractionUuid).getId()
        );
    }

}
