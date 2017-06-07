package test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import domain.ProposalStatus;
import domain.ProposalStatus.proposalStatus;
import service.ProposalStatusService;

public class ProposalStatusServiceTest
{
    private ProposalStatusService service;

    @Before
    public void setUp() throws Exception {
        this.service = new ProposalStatusService(new ProposalStatusRepositoryMock(), new ProposalRepositoryMock());
    }

    @Test
    public void testGetByProposalAndReviewed() {
        List<ProposalStatus> list = service.getByProposalAndReviewed(null);

        Assert.assertEquals(3, list.size());
        Assert.assertEquals(proposalStatus.strongAccept, list.get(0).getStatus());
    }

    @Test
    public void testGetByProposalAndReviewedIgnore() {
        List<ProposalStatus> list = service.getByProposalAndReviewedIgnore(null, ProposalStatusRepositoryMock.user);

        Assert.assertEquals(3, list.size());
        Assert.assertEquals(proposalStatus.strongAccept, list.get(0).getStatus());
    }

    @Test
    public void testGetByProposalWithoutReviews() {
        List<ProposalStatus> list = service.getByProposalWithoutReviews(null);

        Assert.assertEquals(2, list.size());
        Assert.assertEquals(proposalStatus.analyzes, list.get(0).getStatus());
    }

    @Test
    public void testGetByProposalWithAnalyzes() {
        List<ProposalStatus> list = service.getByProposalWithAnalyzes(null);

        Assert.assertEquals(1, list.size());
        Assert.assertEquals(proposalStatus.analyzes, list.get(0).getStatus());
    }

    @Test
    public void testGetByProposalReviwers() {
        List<ProposalStatus> list = service.getByProposalReviwers(null);

        Assert.assertEquals(1, list.size());
        Assert.assertEquals(proposalStatus.toReview, list.get(0).getStatus());
    }

    @Test
    public void testHasPendingProposals() {
        Assert.assertFalse(service.hasPendingProposals(null));
    }

    @Test
    public void testGetProposalStatus() {
        int status = service.getProposalStatus(null);

        Assert.assertEquals(ProposalStatusService.STATUS_ACCEPTED, status);
    }

}
