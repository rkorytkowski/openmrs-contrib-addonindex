package org.openmrs.addonindex.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.openmrs.addonindex.util.Version;

import io.searchbox.annotations.JestId;

/**
 * Details about an OpenMRS add-on and its available versions
 */
public class AddOnInfoAndVersions {
	
	public final static String ES_INDEX = "add_on_info_and_versions";
	
	public final static String ES_TYPE = "add_on_info_and_versions";
	
	@JestId
	private String uid;
	
	private AddOnStatus status;
	
	private AddOnType type;
	
	private String name;
	
	private String description;
	
	private List<String> tags;
	
	private List<Maintainer> maintainers;
	
	private String hostedUrl;
	
	private List<AddOnVersion> versions = new ArrayList<>();
	
	public static AddOnInfoAndVersions from(AddOnToIndex toIndex) {
		AddOnInfoAndVersions ret = new AddOnInfoAndVersions();
		ret.setUid(toIndex.getUid());
		ret.setStatus(toIndex.getStatus());
		ret.setName(toIndex.getName());
		ret.setDescription(toIndex.getDescription());
		ret.setTags(toIndex.getTags());
		ret.setType(toIndex.getType());
		ret.setMaintainers(toIndex.getMaintainers());
		return ret;
	}
	
	public void addVersion(AddOnVersion version) {
		versions.add(version);
		versions.sort(Comparator.reverseOrder());
	}
	
	public int getVersionCount() {
		return versions == null ? 0 : versions.size();
	}
	
	public Version getLatestVersion() {
		return versions == null || versions.size() == 0 ? null : versions.get(0).getVersion();
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public AddOnStatus getStatus() {
		return status;
	}
	
	public void setStatus(AddOnStatus status) {
		this.status = status;
	}
	
	public AddOnType getType() {
		return type;
	}
	
	public void setType(AddOnType type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<String> getTags() {
		return tags;
	}
	
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public List<Maintainer> getMaintainers() {
		return maintainers;
	}
	
	public void setMaintainers(List<Maintainer> maintainers) {
		this.maintainers = maintainers;
	}
	
	public String getHostedUrl() {
		return hostedUrl;
	}
	
	public void setHostedUrl(String hostedUrl) {
		this.hostedUrl = hostedUrl;
	}
	
	public List<AddOnVersion> getVersions() {
		return versions;
	}
	
	public void setVersions(List<AddOnVersion> versions) {
		this.versions = versions;
	}
	
	public Optional<AddOnVersion> getVersion(Version version) {
		if (versions == null) {
			return null;
		}
		return versions.stream().filter(v -> v.getVersion().equals(version)).findFirst();
	}
	
	public void addTag(String tag) {
		if (!Pattern.matches("[^\\s]*", tag)) {
			throw new IllegalArgumentException("Tag cannot contain whitespace:" + tag);
		}
		if (tags == null) {
			tags = new ArrayList<>();
		}
		tags.add(tag);
	}
}
