package org.springframework.social.kakao.api;

public interface FriendsOperation {
	Friends friends(boolean secureResource, Integer offset, Integer limit, String order);
}