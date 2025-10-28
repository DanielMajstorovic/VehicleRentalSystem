<div class="modal fade" id="promoModal" tabindex="-1" aria-labelledby="promoModalLabel">
  <div class="modal-dialog">
    <div class="modal-content">
      <form id="promoForm">
        <div class="modal-header">
          <h5 class="modal-title" id="promoModalLabel">New promotion</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <input class="form-control mb-2" id="promoTitle" placeholder="Title" required>
          <textarea class="form-control mb-2" id="promoDesc" placeholder="Description" required></textarea>

          <label class="form-label mb-0">Starts:</label>
          <input type="datetime-local" id="promoStart" class="form-control mb-2" required>

          <label class="form-label mb-0">Ends:</label>
          <input type="datetime-local" id="promoEnd" class="form-control" required>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-primary">Submit</button>
        </div>
      </form>
    </div>
  </div>
</div>

<div class="modal fade" id="postModal" tabindex="-1" aria-labelledby="postModalLabel">
  <div class="modal-dialog">
    <div class="modal-content">
      <form id="postForm">
        <div class="modal-header">
          <h5 class="modal-title" id="postModalLabel">New post</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <input class="form-control mb-2" id="postTitle" placeholder="Title" required>
          <textarea class="form-control" id="postContent" placeholder="Description" required></textarea>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-primary">Submit</button>
        </div>
      </form>
    </div>
  </div>
</div>
