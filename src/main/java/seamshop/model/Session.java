package seamshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.ForeignKey;

import seamshop.consts.Time;

// TODO: Rename: "UserSession", "RememberMe", "SessionToken", "LoginToken"? (mb, n)
// TODO: Make cron job to clean DB from expired session.
@Entity
@SuppressWarnings("serial")
public class Session extends AbstractExpirableEntity
{
	/*@Column(nullable = false, updatable = false)
	private String subdomain;*/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_session_user_id"))
	private User user;

	@Override
	public long getDurationInMillis()
	{
		return Time.SESSION_DURATION_IN_MILLIS;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
}
